package com.bartlomiejpluta.smnp.error

class ShouldNeverReachThisLineException : Exception(
    "This exception should never be thrown. Please check stack trace and investigate the source of error."
)