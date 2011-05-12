#!/usr/bin/python

import sys
import os

file = sys.argv[1]

alreadyPrint = set()

inStream = open(file, 'r')

for line in inStream.readlines():
	tokens = line.split('\t')
	name = tokens[0]

	if name in alreadyPrint:
		continue
	alreadyPrint.add(name)

	prob = float(tokens[1][:-2].replace(',', '.'))/100
	print "{0}\t{1}".format(name, prob).replace('.',',')


inStream.close()
