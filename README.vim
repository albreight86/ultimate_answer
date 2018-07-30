Utils.java:
This class contains the code to read in the org and user files. It contains
several static methods and a lamda as this is stateless functionality.

User.java
This is the model class for User information.

iOrg.java
- this contains the methods specified in the requirements; some extra methods
are included that seemed necessary. It also extends the iOrgCollections
interface as is seems an Org needed that functionality as well. See below for
more on this.

Org.java 
- This is the model class for a given org containing the org fields, the
children orgs, and the users for this org. It implements the "iOrg" interface
specified in the requirements.

iOrgCollections.java
- this contains the methods specified in the requirements;

OrgCollections.java
- implements iOrgCollections.


To build, run, and run the tests:
    Put a recent TestNG jar and a Jcommander jar in the lib directory.
    The TestNG jar is needed to build and the Jcommander to run the tests.
    Place the java files as shown in the file "javafiles".
    The two files "testOrgInputFile" and "testUserInputFile" are
       created/overwritten by running the tests. They may be modifed
       to include more or less orgs and users when running OrgMain.

    This is what the top level directory should contain:
       classes    src          testOrgInputFile
       javafiles  notes.vim    
       lib        problem.pdf  testng.xml     testUserInputFile

    Run these commands from the top level directory:
    javac -cp lib/* -d classes @javafiles
    java -cp lib/*:classes com.arb.OrgMain
    java -cp lib/*:classes org.testng.TestNG testng.xml

Thoughts and comments:
- An org with an ID of 0 is created to handle orgs with "null" for a parent.
  This was needed so these orgs could be treated as any child org.
- The comparators in the User and Org classes are not strictly needed; but 
  they are useful for omitting duplicate data as well as ordering 
  for ease of viewing output.
- Incorrectly formatted lines in each input file are replaced with "NULL"
  versions of the given object. 
- The map/reduce pattern was used to generate the data requested.
- The first thing I might investigate in a refactor: as implemented,
  an Org "isA' "OrgCollection" where a "hasA" relationship might be better. 
  It seems like it could be viewed as either type of relationship.
  An option might be to change these interfaces somewhat.
- The extra methods required in the iOrg interface, and the need for it to
  extend the iOrgCollections iterface, leave me with the notion that I
  am missing something implied in the requirements.
- If there were 500 million rows, where would this implementation break?
   - It could run out of memory when reading in the data files as they are 
     read in their entirety before processing begins.
   - If the tree is deep, rather than broad, overloading the
     stack could occur since recursion is used and goes from the top to the
     bottom through all paths in one operation. There may be ways to break 
     up the calcuations and perform smaller calculations when users and orgs
     are added.


