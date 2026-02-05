package sg.edu.nus.iss.d13revision;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import sg.edu.nus.iss.d13revision.controllers.DataController;

@SpringBootTest
@AutoConfigureMockMvc
public class DataControllerTest {

    @Autowired
    DataController dataController;


    @Test
    void health(){
        assertEquals("HEALTH CHECK OK!", dataController.healthCheck());
    }

    @Test
    void version(){
        assertEquals("The actual version is 1.0.0", dataController.version());
    }

    @Test
    void nationsLength(){
        Integer nationLength = dataController.getRandomNations().size();
        assertEquals(10, nationLength);
    }

    @Test
    void currenciesLength(){
        Integer currencyLength = dataController.getRandomCurrencies().size();
        assertEquals(20, currencyLength);
    }


    // @Autowired
    // private MockMvc mockMvc;

    // @Test
    // public void testHealthCheck() throws Exception {
    //     mockMvc.perform(get("/"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("HEALTH CHECK OK!"));
    // }

    // @Test
    // public void testVersion() throws Exception {
    //     mockMvc.perform(get("/version"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("The actual version is 1.0.0"));
    // }

    // @Test
    // public void testGetRandomNations() throws Exception {
    //     mockMvc.perform(get("/nations"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.length()").value(10));
    // }

    // @Test
    // public void testGetRandomCurrencies() throws Exception {
    //     mockMvc.perform(get("/currencies"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.length()").value(20));
    // }
}