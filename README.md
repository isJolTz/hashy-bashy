# hashy-bashy
Compare and authenticate file with its publicly declared hash values

## How it works currently
Run the HashyApplication.java with the following arguments:
1. Hash algorithm string (SHA1, MD5, etc...)
2. Location of the file to be digested
3. Location of the comparison hash digest (expected result)

## Modifications to make
Want this to have a simple UI for selecting the files, hash algorithm, and computing the result. My attempt will be with Swing to avoid web interfaces, though wanting to learn more about creating a UI for a local application.
