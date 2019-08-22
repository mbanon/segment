# segment

## Introduction

Segment program is used to split text into segments, for example sentences.

Splitting rules are read from SRX file, which is standard format for this task. 

This fork provides a custom version of [loomchild/segment](https://github.com/loomchild/segment), enhanced to be easily wrapped and called from a Python program (for example, [Bifixer](http://github.com/bitextor/bifixer)). For this purpose, a new option (`-c`) has been added. This allows to load the segmenter in memory, ready to be invoked one-line-at-a-time each time it's needed. See below for an example of usage from Python.

## Requirements

To run the project Java Runtime Environment (JRE) 1.8 is required. 

Program should run on any operating system supported by Java. 

## Installation

Clone this repository: 

```bash
git clone https://github.com/mbanon/segment.git
```
If you are not using Java8 as default, download it and overwrite the 'JAVA_HOME' variable: 

```bash 
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/
```
Now compile and install:
```bash
cd segment/segment
mvn clean install
cd ../segment/segment-ui
mvn clean install
cd target
unzip unzip segment-2.0.2-SNAPSHOT.zip
```

## Running

Example:

```bash
LC=en; \
java -cp segment-ui-2.0.2-SNAPSHOT.jar:./segment-2.0.2-SNAPSHOT/lib/* net.loomchild.segment.ui.console.Segment\ 
-l $LC \
-i inputfile \
-o outputfile \
-s ../../srx/language_tools.segment.srx 
```

## SRX files 

Some SRX files are provided in the `srx` folder:

* language_tools.segment.srx : [LanguageTool](https://github.com/languagetool-org/languagetool) rules
* OmegaT.srx: official [OmegaT](https://omegat.org/) segmentation rules.
* PTDR.srx : Modified OmegaT segmentation rules
* Aggressive.srx : segments by all punctuation marks:  .,;:!?
* NonAggressive.srx : segments by .;:!? (that is, all punctuation marks except comma)

If the parameter `-s` is not used, a default SRX file will be used.

Don't hesitate to build your own SRX files! Standard SRX 2.0 specs can be found [here](https://www.gala-global.org/srx-20-april-7-2008). 

## Benchmarks

Some benchmarks results can be found [here](https://docs.google.com/spreadsheets/d/1mGJ9MSyMlsK0EUDRC2J50uxApiti3ggnlrzAWn8rkMg/edit?usp=sharing).

Benchmarks ran on a Intel(R) Core(TM) i5-4460 CPU @ 3.20GHz machine, using [Universal Dependecies](https://universaldependencies.org) datasets and gold standards. 

Example: testing the English dataset with the Language Tools SRX rules:


```bash
L=English; LC=en;\
time java -cp segment-ui-2.0.2-SNAPSHOT.jar:./segment-2.0.2-SNAPSHOT/lib/* net.loomchild.segment.ui.console.Segment \
-l $LC -i pipeline_evaluation_data/sentence_splitting/UD_$L.dataset -o loomchild.language-tools.$LC \
-s ../../srx/language_tools.segment.srx  \
&& python3.6 segmenteval.py pipeline_evaluation_data/sentence_splitting/UD_$L.dataset.gold loomchild.$LC
```

The `segmenteval.py` script can be downloaded from [here](https://gist.github.com/mbanon/73b3f5db5c25cd660228fed283a3821f)

(Other segmenting tools, such as [Moses](https://github.com/kpu/preprocess/blob/master/moses/ems/support/split-sentences.perl), [Ulysses](https://sourceforge.net/projects/bitextor/files/bitextor/bitextor-5.0/)  and [NTLK](https://www.nltk.org/_modules/nltk/tokenize.html#sent_tokenize), are included in the benchmarks)

## Python wrapping example

Visit [this branch](https://github.com/bitextor/bifixer/tree/segmenter-tests)(http://github.com/bitextor/bifixer) of [Bifixer](http://github.com/bitextor/bifixer) to find a super simple example of a Python3 program that reads a file line-by-line, performs some operations on each line, and then calls the Java segmenter for each line, by using [ToolWrapper](https://pypi.org/project/toolwrapper/).
  * [Implementation of the segmenter class](https://github.com/bitextor/bifixer/blob/segmenter-tests/bifixer/segmenter.py)
  * [Load segmenter object](https://github.com/bitextor/bifixer/blob/segmenter-tests/bifixer/bifixer.py#L114)

## More information

More detailed information can be found [here](https://github.com/loomchild/segment/blob/master/README.md).

You can reach me by email at `mbanon[at]prompsit[dot]com`
