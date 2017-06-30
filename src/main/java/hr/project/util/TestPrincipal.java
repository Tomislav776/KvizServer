package hr.project.util;

import java.security.Principal;

public class TestPrincipal  implements Principal {

    private final String name;


    public TestPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

}