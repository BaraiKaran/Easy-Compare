# Easy-Compare

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/253975d4154c4766a8752c571ccef874)](https://app.codacy.com/app/barai.k/Easy-Compare?utm_source=github.com&utm_medium=referral&utm_content=BaraiKaran/Easy-Compare&utm_campaign=Badge_Grade_Dashboard) [![CircleCI](https://circleci.com/gh/BaraiKaran/Easy-Compare/tree/master.svg?style=svg)](https://circleci.com/gh/BaraiKaran/Easy-Compare/tree/master)

Scala application to compare documents

### Introduction
Easy Compare is a document comparison application that helps to detect plagiarism. Every document follows a similar text preprocessing approach, every sentence in the document gets hashed using Murmur3 hashing technique and this hash is stored in Postgresql. User can now select two documents and get a plagiarism score which is calculated using Jaccard index.

### Jaccard Similarity
The Jaccard index, also known as Intersection over Union and the Jaccard similarity coefficient  is a statistic used for comparing the similarity and diversity of sample sets. The Jaccard coefficient measures similarity between finite sample sets, and is defined as the size of the intersection divided by the size of the union of the sample sets.

### MurMur Hash 3
Murmur Hash 3: MurmurHash is a non-cryptographic hash function suitable for general hash-based lookup. The name comes from two basic operations, multiply (MU) and rotate (R), used in its inner loop. <https://en.wikipedia.org/wiki/MurmurHash#MurmurHash3>

### Text Preprocessing 
Text preprocessing function reads the input file and converts all the text to lowercase and combines every line of the document into a string and removes all the whitespaces, each sentenced is hash and stored in Postgresql.