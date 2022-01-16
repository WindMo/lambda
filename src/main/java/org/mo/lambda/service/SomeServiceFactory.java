package org.mo.lambda.service;

/**
 * @author WindShadow
 * @version 2022-01-15.
 */

public class SomeServiceFactory {

    private final String someField = "SomeServiceFactory.someField";

    public SomeService getLambdaSomeService() {

       return () -> {

            System.out.println(this.getClass());
            System.out.println(this.someField);
        };
    }

    public SomeService getAnonymousSomeService() {

        return new SomeService() {
            @Override
            public void say() {

                System.out.println(this.getClass());
//                System.out.println(this.someField); // compilation fails
            }
        };
    }
}
