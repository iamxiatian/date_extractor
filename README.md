# date_extractor
Date Extractor: Extracting date from free text.

This module is extracted from x-extractor project.

## How to use

```
import ruc.irm.xextractor.date.DateExtractor;

Date d = DateExtractor.extract( "December 21, 2016 | 3:32pm");

System.out.println(d);
```

The output should be:

```
Wed Dec 21 15:32:00 CST 2016
```

## How to Compile and Package
Run:

```
sbt package
```

Then copy the target/date_extractor-0.1.jar to your project library.

If you want to modify the code in an IDE like Intellij or Eclipse, use related sbt command to generate the project file. Take IntelliJ as an example, just run the following shell command:

```
sbt gen-idea
```

If you want to contribute the code, please contact me, thanks.
