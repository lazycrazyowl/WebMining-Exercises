import os
import tarfile
import shutil
import lzma
import time
import datetime
import zlib
import sets
import multiprocessing

class Result:
    def __init__(self, testClass, testedFile, mode, time, compressedSize, originSize):
        self.testClass = testClass.split('.')[0]
        self.testedFile = testedFile
        self.mode = mode
        self.time = time
        self.compressedSize = compressedSize
        self.originSize = originSize
        
    def ratio(self):
        return 1.0*self.compressedSize / self.originSize
    
    def __repr__(self):
        return self.__str__()
             
    def __str__(self):
        return "Class {3}({2}): Took {0}, Compressed: {1}, Origin: {5}, Ratio {6}".format(self.time, 
        self.compressedSize, self.mode, self.testClass, self.testedFile, self.originSize, self.ratio())

class LzmaCompressor:
    def compress(self, archive):
        return lzma.compress(open(archive, 'r').read())
        
    def __str__(self):
        return "LZMA"
    def __unicode__(self):
        return self.__str__()
        
class GzipCompressor:
    def compress(self, archive):
       	return zlib.compress(open(archive, 'r').read())
        
    def __str__(self):
        return "GZip"
        

class ResultTableEntry:
    fileName = ""
    typeClass = ""
    # Result is a dict of dicts.
    results = {}
    
    def __init__(self, fileName, typeClass, results):
        self.fileName = fileName
        self.typeClass = typeClass
        self.results = results

class ResultTable:
    resultEntries = []
    
    def __init__(self):
        pass
    
    def append(self, fileName, realClass, resultList):
        resultDict = {}
        
        for entry in resultList:
            
            if not (entry.mode in resultDict):
                resultDict[entry.mode] = {}
                
            resultDict[entry.mode][entry.testClass] = entry.ratio()
        
        e = ResultTableEntry(fileName=fileName, typeClass=realClass, results=resultDict)
        self.resultEntries.append(e)
        
    def __str__(self):
        result = ""
        
        for e in self.resultEntries:
            result += "{0};{1}->".format(e.fileName, e.typeClass)
            for (compressType, values) in e.results.items():
                for name, ratio in values.items():
                    result += "{0}.{1}={2},".format(compressType, name, ratio)
            result += "\n"
        
        return result
        
    def merge(self, other):
        self.resultEntries.extend(other.resultEntries)
        return len(self.resultEntries)
    
    def csv(self):
        
        compress = sets.Set(self.resultEntries[0].results.keys())
        
        classes = sets.Set()
        for s in [val for (key,val) in self.resultEntries[0].results.items()]:
            print s.keys()
            classes = classes.union(s.keys())
            
        #write header
        print compress
        print classes
        
        result = "filename;realClass"
        for c in compress:
            for s in classes:
                result += "{0}.{1};".format(c,s)
        result += "\n"
        
        for row in self.resultEntries:
            result += "{0};{1};".format(row.fileName, row.typeClass)
            for c in compress:
            	for typ in classes:
            	    result+= "{0};".format(row.results[c][typ])
           	result += "\n"
        
        return result

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
    
    def addClass(self, tarFile):
        self.classes.append(tarFile)
        self.originSize[tarFile] = len(self.compressor.compress(tarFile))
        
        pass
        
    def appendFile(self, tarFile, fileToAppend):
   	    t = tarfile.open(name = tarFile, mode = 'a')
   	    t.add(fileToAppend, os.path.basename(fileToAppend))
    
    def testForClass(self, theClass, testFile):
        archive = theClass+str(self.classificationNr)
        shutil.copyfile(theClass, archive)
        
        self.appendFile(archive, testFile)
        
        start = datetime.datetime.now()
        size = len(self.compressor.compress(archive))
        tookTime = datetime.datetime.now()-start
        
        originSize = self.originSize[theClass]
        
        os.remove(archive)
        r= Result(os.path.basename(theClass), testFile, str(self.compressor), tookTime,size, originSize)
        
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
    classificator = Classificator(GzipCompressor(),"/home/frank/Desktop/train", testRunner.id)
    matrix = ResultMatrix("Matrix Result",runner.tests.keys())
    
    table = ResultTable()
    
    testCount = reduce(lambda x,y:x+y,[len(v) for (k,v) in testRunner.tests.items()])
    testRunCount = 0
    for classType,testFiles in testRunner.tests.items():
        print classType
        for test in testFiles:
            
            result = classificator.classify(test)
            sortedResult = sorted(result, key=lambda a: a.ratio())
            
            v = matrix.getitem(classType,sortedResult[0].testClass)
            matrix.setitem(classType,sortedResult[0].testClass, v+1)
            table.append(classType, test,result)
            
            print "\tTest {0} of {1} real: {2} classified: {3}".format(testRunCount, testCount, classType, sortedResult[0].testClass)
            testRunCount += 1
    
    print "test done"
    return (table, matrix)
    
    

if __name__ == '__main__':
    runner = TestRunner.fromDirectory("/home/frank/Desktop/test")
    runners = runner.split(8)
    
    table = ResultTable()
    matrix = ResultMatrix("Matrix", runner.tests.keys())

    pool = multiprocessing.Pool(processes=2)

    for (t,m) in pool.map(runTest, runners):
        table.merge(t)
        matrix.add(m)
        
    print table
    print matrix
        
    f = open('/home/frank/Desktop/gzipMatrix.txt', 'w')
    f.write(matrix.__repr__())
    f.flush()
    f.close()
    
    f2 = open('/home/frank/Desktop/gzipResult.csv', 'w')
    f2.write(table.csv())
    f2.flush()
    f2.close()
    
    
            
    
#    
#    testFile = "/home/frank/Desktop/test/course/cornell/http:^^cs.cornell.edu^Info^Courses^Fall-95^CS415^CS415.html"
#    result = classificator.classify(testFile)
#    table = ResultTable()
#    
#    table.append(os.path.basename(testFile), "course", result)
#    print table.csv()
