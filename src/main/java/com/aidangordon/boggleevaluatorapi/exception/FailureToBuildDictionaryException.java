package com.aidangordon.boggleevaluatorapi.exception;

import java.io.IOException;

public class FailureToBuildDictionaryException extends Exception {
    public FailureToBuildDictionaryException(final Exception e) {
        super(e);
    }
}
