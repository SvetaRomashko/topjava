package ru.javawebinar.topjava.web;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.api.UrlAssert;
import org.junit.jupiter.api.Test;

public class ResourceControllerTest extends AbstractControllerTest{

    @Test
    void getStyle() throws Exception{
        perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/css;charset=UTF-8"));

    }
}
