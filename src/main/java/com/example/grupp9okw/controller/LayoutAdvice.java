package com.example.grupp9okw.controller;

import ch.qos.logback.core.Layout;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.Writer;

@ControllerAdvice
class LayoutAdvice {

    //skapar ett objekt med identifieraren "layout" av typen Mustache.Lambda
    @ModelAttribute("layout")
    public Mustache.Lambda layout() {
        return new Layout();
    }

    class Layout implements Mustache.Lambda {
        String body;
        @Override
        public void execute(Template.Fragment frag, Writer out) throws IOException {
            body = frag.execute();
            //tilldelar String variabeln body resultatet av renderingen i layout-attributet
            //fångar sen in detta med mustache taggarna i view:n {{{layout.body}}}
        }
    }
}
