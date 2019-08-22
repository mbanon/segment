# segment

## Introduction

Segment program is used to split text into segments, for example sentences.
Splitting rules are read from SRX file, which is standard format for this task. 

This fork provides a custom version of [loomchild/segment](https://github.com/loomchild/segment), enhanced to be easily wrapped and called from a Python program ([Bifixer](http://github.com/bitextor/bifixer))

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
```bash
L=Bulgarian; LC=bg; time java -cp segment-ui-2.0.2-SNAPSHOT.jar:./segment-2.0.2-SNAPSHOT/lib/* net.loomchild.segment.ui.console.Segment -l $LC -i /home/mbanon/project/pipeline_evaluation_data/sentence_splitting/UD_$L.dataset -o /home/mbanon/project/pipeline_evaluation_data/sentence_splitting/UD_$L.output -s /home/mbanon/Escritorio/srx/language_tools.segment-v2.srx  && python3.6 /home/mbanon/segmenteval.py /home/mbanon/project/pipeline_evaluation_data/sentence_splitting/UD_$L.dataset.gold /home/mbanon/project/pipeline_evaluation_data/sentence_splitting/UD_$L.output
```

## SRX files 

Some SRX files are provided in the `srx` folder:

* OmegaT.srx
* OmegaT-PrompsitForTDR.srx
* PrompsitAggressive.srx
* PrompsitNonAggressive.srx
* language_tools.segment.srx

If the parameter `-s` is not used, a default SRX file will be used.
Don't hesitate to use your own!

## Benchmark


## More information

More detailled information can be found [here](https://github.com/loomchild/segment/blob/master/README.md).
