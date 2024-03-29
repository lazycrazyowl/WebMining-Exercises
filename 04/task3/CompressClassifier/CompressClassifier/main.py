import os
import tarfile
import shutil
import lzma
import time
import datetime
import zlib
import copy
import sets
import multiprocessing
import pickle

class Result:
    def __init__(self, testClass, testedFile, time, compressedSize, originSize):
        self.testClass = testClass.split('.')[0]
        self.testedFile = testedFile
        self.time = time
        self.compressedSize = compressedSize
        self.originSize = originSize
        
    def ratio(self):
        return 1.0 * self.compressedSize / self.originSize
    
    def __repr__(self):
        return self.__str__()
             
    def __str__(self):
        return "Class {2}: Took {0}, Compressed: {1}, Origin: {4}, Ratio {5}".format(self.time, 
        self.compressedSize, self.testClass, self.testedFile, self.originSize, self.ratio())

class Classificator:
    
    class_counter= 0

    def prepareStep(self, trainDir):
	    names = os.listdir(trainDir)
	    
	    for name in  names:
	        fullpath = os.path.join(trainDir, name)
	
	        if (not os.path.isdir(fullpath)):
	            print "{0} is not a dir".format(fullpath)
	            continue
	            
	        if os.path.exists(fullpath+".tar"):
	            self.addClass(fullpath+".tar")
	            continue
	       
	        print "creating {0}.tar".format(name) 
	        
	        t = tarfile.open(name = fullpath+".tar", mode = 'w')
	        t.add(fullpath, "")
	        t.close()
	        
    
    originSize = {}
    uncompressedSize = {}
    originCompressor = {}
    flushSize = {}
    
    def addClass(self, tarFile):
        self.classes.append(tarFile)
        
        self.uncompressedSize[tarFile] = len(open(tarFile, 'r').read())
        
        c = self.compressor.copy()
        cstr = c.compress(open(tarFile, 'r').read())
        self.originCompressor[tarFile] = c.copy()
        flushsize = len(c.flush())
        self.originSize[tarFile] = len(cstr)+ flushsize
        self.flushSize[tarFile] = flushsize
        
        pass
        
    def appendFile(self, tarFile, fileToAppend):
   	    t = tarfile.open(name = tarFile, mode = 'a')
   	    t.add(fileToAppend, os.path.basename(fileToAppend))
    
    def testForClass(self, theClass, testFile):
        
        start = datetime.datetime.now()
        compressor = self.originCompressor[theClass].copy()
        testfileSize = len(open(testFile, 'r').read())
        deltasize = len(compressor.compress(open(testFile, 'r').read()))
        deltasize += len(compressor.flush()) - self.flushSize[theClass]
        tookTime = datetime.datetime.now()-start
        
        originSize = self.originSize[theClass]
        
        #r= Result(os.path.basename(theClass), testFile, tookTime,originSize+deltasize, originSize)
        r= Result(os.path.basename(theClass), testFile, tookTime, deltasize, testfileSize)
#        print "\t {0} has ratio {1}({2}/{3})".format(os.path.basename(archive),r.ratio(),size,originSize)
        return r
        
    def classify(self, testFile):
        result = []
        for classArchive in self.classes:
            result.append(self.testForClass(classArchive, testFile))
            
        return result

    
    def __init__(self, compressor, trainDir, id):
        self.compressor = compressor
        self.classes = []
        
        self.classificationNr = id
        
        self.prepareStep(trainDir)
        
    def __str__(self):
        result = ""
        for s in self.classes:
            result += s+"\n"
        return result

class TestRunner:
    @classmethod
    def getClasses(cls, testDir):
        names = os.listdir(testDir)
        return [n for n in names if os.path.isdir(os.path.join(testDir,n))]
    
    @classmethod
    def findAllFiles(cls, dir):
        names = os.listdir(dir)
        dirs = [d for d in names if os.path.isdir(os.path.join(dir,d))]
        files = [os.path.join(dir, f) for f in names if os.path.isfile(os.path.join(dir,f))]
        
        for d in dirs:
            files.extend(TestRunner.findAllFiles(os.path.join(dir,d)))
            
        return files
    
    @classmethod
    def loadTestFiles(cls, testDir):
        tests = {}
        for c in TestRunner.getClasses(testDir):
            tests[c]=TestRunner.findAllFiles(os.path.join(testDir, c))
            
        return tests
    
    def __init__(self, id, testDir, tests):
        self.id = id
        self.testDir = testDir
        self.tests = tests
    
    @classmethod
    def fromDirectory(cls,testDir):
        return cls(0,testDir,TestRunner.loadTestFiles(testDir))
    
    def count(self):
        return reduce(lambda a,b:a+b,[len(n) for n in self.tests.values()])
    
    def __repr__(self):
        r = ""
        for (key, items) in self.tests.items():
        	r+= key+"\n\t" + reduce(lambda a,b: a+"\n\t"+b, items)+"\n"
        return r
        
    def split(self, chunkCount):
        testCount = reduce(lambda a,b:a+b, [len(n) for n in self.tests])
        testsPerChunk = testCount / chunkCount
        
        runnersTests = []
        for i in range(chunkCount):
            tests = {}
            for classType in self.tests:
                tests[classType] = []
            runnersTests.append(tests)
        
        count = 0
        for (typeClass, testList) in self.tests.items():
            for test in testList:
                runnersTests[count%chunkCount][typeClass].append(test)
                count+=1
        
        runners = []
        for testDict in runnersTests:
            runners.append(TestRunner(len(runners), self.testDir, testDict))
        
        return runners
        
class ResultMatrix:
    
    def __init__(self, name, names):
        self.name = name
        self.cols = len(names)
        self.rows = len(names)
        # initialize matrix and fill with zeroes
        self.matrix = []
        for i in range(self.rows):
            ea_row = []
            for j in range(self.cols):
                ea_row.append(0)
            self.matrix.append(ea_row)
        
        self.nameTable = {}
        i = 0;
        for n in names:
            self.nameTable[n]=i;
            i += 1
 
    def setitem(self, realName, testedName, v):
        rowIdx = self.nameTable[realName]
        colIdx = self.nameTable[testedName]
        self.matrix[rowIdx][colIdx] = v
 
    def getitem(self, realName, testedName):
        rowIdx = self.nameTable[realName]
        colIdx = self.nameTable[testedName]
        return self.matrix[rowIdx][colIdx]
 	
    def add(self, o):
 	    for i in range(self.cols):
 	        for j in range(self.rows):
 	            self.matrix[i][j]+=o.matrix[i][j]
 	    return 1 
 
    def __repr__(self):
        outStr = "{0:#^80}\n".format(self.name)
        formatStr = "{0:<20}"
        outStr += formatStr.format("real\\classified")
        
        for rowName in self.nameTable.keys():
        	outStr += formatStr.format(rowName)
        outStr += "\n"
        for rowName in self.nameTable.keys():
            outStr += formatStr.format(rowName)
            for colName in self.nameTable.keys():
                outStr += formatStr.format(self.getitem(rowName,colName))
            outStr += "\n"
        return outStr

def runTest(testRunner):
    
    classificator = Classificator(zlib.compressobj(),"/home/frank/Desktop/train", testRunner.id)
    matrix = ResultMatrix("Matrix Result",runner.tests.keys())
    
    testCount = reduce(lambda x,y:x+y,[len(v) for (k,v) in testRunner.tests.items()])
    testRunCount = 0
    for classType,testFiles in testRunner.tests.items():
        print classType
        for test in testFiles:
            
            result = classificator.classify(test)
            sortedResult = sorted(result, key=lambda a: a.ratio())
            print sortedResult
            v = matrix.getitem(classType,sortedResult[0].testClass)
            matrix.setitem(classType,sortedResult[0].testClass, v+1)
            
            print "\tTest {0} of {1} real: {2} classified: {3}, ratio {4}".format(testRunCount, testCount, classType, sortedResult[0].testClass, sortedResult[0].ratio())
            testRunCount += 1
    
    print "test done"
    return matrix
    
    

if __name__ == '__main__':
    lzmaC = lzma.LZMACompressor()
    
    runner = TestRunner.fromDirectory("/home/frank/Desktop/test")
    runners = runner.split(2)
    
    matrix = ResultMatrix("Matrix", runner.tests.keys())

    pool = multiprocessing.Pool(processes=2)

    for m in pool.map(runTest, runners):
        matrix.add(m)
        
        
    f = open('/home/frank/Desktop/Matrix.txt', 'w')
    f.write(matrix.__repr__())
    f.flush()
    f.close()
    
    
            
    
#    
#    testFile = "/home/frank/Desktop/test/course/cornell/http:^^cs.cornell.edu^Info^Courses^Fall-95^CS415^CS415.html"
#    result = classificator.classify(testFile)
#    table = ResultTable()
#    
#    table.append(os.path.basename(testFile), "course", result)
#    print table.csv()
