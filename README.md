# hashy-bashy
Compare and authenticate file with its publicly declared hash values

## How it works currently
Run the HashyApplication.java with the following arguments:
1. Hash algorithm string (SHA1, MD5, etc...)
2. Location of the file to be digested
3. Location of the comparison hash digest (expected result)

## Modifications to make
Want this to have a simple UI for selecting the files, hash algorithm, and computing the result.

- I've become more familiar with hash and encryption tools in standard CLI/terminal, so this stopped interesting me. Having them easily compared and showing a "Green Light" for files compared against their public hashes/signatures would be great. Might be worth continuing soon, but would need some thought for 
1. how to present things rather than just implementing hash algorithms,
2. simplifying how to check for files and other things to perform a hash/sign compare
3. ... something more advanced.
