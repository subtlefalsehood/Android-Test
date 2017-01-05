package com.training.storage.model;

import java.io.InputStream;
import java.util.List;

/**
 * Created by chenqiuyi on 17/1/4.
 */

public interface RecipeParser {
    public List<RecipeInfo> parser(InputStream is) throws Exception;

    public String serialize(List<RecipeInfo> infos) throws Exception;
}
