package sg.edu.nus.iss.d13revision;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import sg.edu.nus.iss.d13revision.models.Person;
import sg.edu.nus.iss.d13revision.services.PersonService;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    private List<Person> personList;

    @BeforeEach
    public void setUp() {
        personList = new ArrayList<>();
        personList.add(new Person("John", "Doe"));
        personList.add(new Person("Jane", "Doe"));
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void testGetAllPersons() throws Exception {
        when(personService.getPersons()).thenReturn(personList);
        mockMvc.perform(get("/person/testRetrieve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testPersonList() throws Exception {
        when(personService.getPersons()).thenReturn(personList);
        mockMvc.perform(get("/person/personList"))
                .andExpect(status().isOk())
                .andExpect(view().name("personList"))
                .andExpect(model().attribute("persons", personList));
    }

    @Test
    public void testShowAddPersonPage() throws Exception {
        mockMvc.perform(get("/person/addPerson"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeExists("personForm"));
    }

    @Test
    public void testSavePerson() throws Exception {
        mockMvc.perform(post("/person/addPerson")
                .param("firstName", "John")
                .param("lastName", "Doe"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/person/personList"));
    }

    @Test
    public void testSavePerson_emptyFields() throws Exception {
        mockMvc.perform(post("/person/addPerson")
                .param("firstName", "")
                .param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("addPerson"))
                .andExpect(model().attributeExists("errorMessage"));
    }

     @Test
    public void testPersonToEdit() throws Exception {
        Person p = new Person("John", "Doe");
        mockMvc.perform(post("/person/personToEdit").flashAttr("per", p))
                .andExpect(status().isOk())
                .andExpect(view().name("editPerson"))
                .andExpect(model().attribute("per", p));
    }

    @Test
    public void testPersonEdit() throws Exception {
        Person p = new Person("John", "Doe");
        mockMvc.perform(post("/person/personEdit").flashAttr("per", p))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/person/personList"));
    }

    @Test
    public void testPersonDelete() throws Exception {
        Person p = new Person("John", "Doe");
        mockMvc.perform(post("/person/personDelete").flashAttr("per", p))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/person/personList"));
    }
}