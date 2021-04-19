tests:
	@/usr/bin/time ./gradlew clean build test

integrationtests:
	@/usr/bin/time ./gradlew clean integrationTest

travis-tests:
	@/usr/bin/time ./gradlew clean build test

travis-integrationtests:
	@/usr/bin/time ./gradlew clean integrationTest
