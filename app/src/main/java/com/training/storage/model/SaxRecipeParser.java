package com.training.storage.model;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by chenqiuyi on 17/1/4.
 */

public class SaxRecipeParser implements RecipeParser {
    @Override
    public List<RecipeInfo> parser(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        MyHandler myHandler = new MyHandler();
        saxParser.parse(is, myHandler);
        return myHandler.getRecipes();
    }

    @Override
    public String serialize(List<RecipeInfo> infos) {
        return null;
    }

    private class MyHandler extends DefaultHandler {
        private List<RecipeInfo> recipes;

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            recipes = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        public List<RecipeInfo> getRecipes() {
            return recipes;
        }
    }
}
