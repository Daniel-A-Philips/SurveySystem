Survey System — SE 310 Homework 2 Part B
---------
A console-based Survey application that lets the user create, display, save, load, take, and modify surveys built from six question types: True/False, Multiple Choice, Short Answer, Essay, Valid Date, and matching. All persistence is done via Java serialization.

Layout:
SurveySystem/
  README.txt    - this file
  src/  - all .java source files
  surveys/   - serialized surveys (.ser)
    SampleSurvey.ser    - sample survey containing one of every
            question type (use option 3 to load)
  responses/    - serialized survey responses are written
    here automatically when a survey is taken

All paths used by the program are relative.

reqs
JDK 8 or newer

compile instructions
-----------------------
From inside the SurveySystem/ folder:

    javac -d . src/*.java

This produces .class files in the current directory. There is no package, so all classes sit in the default package.

Run instructions
-----
From the SurveySystem/ folder, after compiling:
    java SurveyApp
You will see Menu 1. Use option 3 to load surveys/SampleSurvey.ser

Build Sample Survey
-------
I created a helper class that programmatically builds a sample survey containing one of every question type and writes it to surveys/SampleSurvey.ser. to regenerate it:
    java SampleSurveyBuilder
This is NOT part of the main application, just there so the sample file can be re-created in case it is lost.

Flow
-------------
Menu 1 (the main menu):
  1) Create a new Survey
  2) Display an existing Survey
  3) Load an existing Survey
  4) Save the current Survey
  5) Take the current Survey
  6) Modify the current Survey
  7) Quit

Menu 2 (shown after option 1, for adding questions):
  1) Add a new T/F question
  2) Add a new multiple-choice question
  3) Add a new short answer question
  4) Add a new essay question
  5) Add a new date question
  6) Add a new matching question
  7) Return to previous menu

For multiple choice / short answer / essay / date questions, after entering the prompt the user is asked whether the question should accept multiple responses, and if so, how many.

Succesfully Implemented
-------------------
* Driver / text menu
* Create:  T/F, MC, Short Answer, Essay, Date, Matching
* Single response per question
* Multiple responses per question
* Display: all six question types
* Load (with menu of files to pick from)
* Save (serialization)
* Modify: all six question types (prompt + fields)
* Take: all six question types, with response saved
        to a unique file in responses/
* Improper-input handling everywhere (numeric input,
  empty strings, bad dates, out-of-range menu picks)
* The four required "no survey loaded" message

Notes on my design
------------
* This of course is based on my UML from 2A, a photo of which is included in this submission
* Question and Answer are abstract base classes; the six concrete question types each pair with a corresponding answer type.
* Survey owns a List<Question>; Response owns a List<Answer>.
* All persistent classes implement java.io.Serializable and declare an @Serial serialVersionUID.
* InputHelper centralizes all reading from System.in so improper input (empty strings, non-numeric, out-of-range, malformed dates) is rejected with a clear message and the user is re-prompted, the program never crashes on bad input.
* SurveyFileManager owns all file I/O. Filenames the user supplies are sanitized (path separators stripped, whitespace replaced with underscores) before being used.
* Date validation uses java.time.LocalDate with strict resolver style, e.g. "1990-13-01" or "1990-02-30" are rejected.

Issues:
None (I'm aware it is optimistic but a man can dream)

FILES IN src/
-------------
SurveyApp.java - the main driver (entry point)
Menu.java - text-menu controller
InputHelper.java - safe input reading
SurveyFileManager.java - serialization to/from disk
Survey.java - the Survey class
Response.java - the Response class
Question.java - abstract base class
TrueFalseQuestion.java
MultipleChoiceQuestion.java
ShortAnswerQuestion.java
EssayQuestion.java
ValidDateQuestion.java
MatchingQuestion.java
Answer.java - abstract base class
TrueFalseAnswer.java
MultipleChoiceAnswer.java
ShortAnswerAnswer.java
EssayAnswer.java
DateAnswer.java
MatchingAnswer.java
SampleSurveyBuilder.java - utility to regenerate SampleSurvey.ser
