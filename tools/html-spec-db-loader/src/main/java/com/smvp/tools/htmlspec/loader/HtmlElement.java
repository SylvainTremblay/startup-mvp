package com.smvp.tools.htmlspec.loader;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class HtmlElement {

    private String name;

    private String description;

    private List<String> attributes = new ArrayList<>();

    public HtmlElement(String name) {
        this.name = name;
    }
}
