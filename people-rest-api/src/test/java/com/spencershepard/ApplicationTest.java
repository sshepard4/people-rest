package com.spencershepard;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.spencershepard.family.Family;
import com.spencershepard.family.FamilyRepository;
import com.spencershepard.people.Gender;
import com.spencershepard.people.Person;
import com.spencershepard.people.PersonRepository;

/**
 * Test retrieving people and families using the application
 * 
 * @author spencer.shepard
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            "hal+json");

    private MockMvc mockMvc;

    private Person father;
    
    private Family family;
    
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private FamilyRepository familyRepository;
    
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    /**
     * Setup the mock and reset the repository data.
     * 
     */
    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.familyRepository.deleteAll();
        this.personRepository.deleteAll();

        father = personRepository.save(new Person().setFirstName("Al").setLastName("Bundy")
                .setBirthDate(LocalDate.of(1960, 1, 2)).setAddress("123 Sesame St").setGender(Gender.male));
        Person mother = personRepository.save(new Person().setFirstName("Peg").setLastName("Bundy")
                .setBirthDate(LocalDate.of(1981, 2, 28)).setAddress("123 Sesame St").setGender(Gender.female));
        Set<Person> children = new HashSet<>();
        children.add(personRepository.save(new Person().setFirstName("Christina").setLastName("Applegate")
                .setBirthDate(LocalDate.of(2001, 3, 31)).setAddress("123 Sesame St").setGender(Gender.male)));
        children.add(personRepository.save(new Person().setFirstName("Bud").setLastName("Williams")
                .setBirthDate(LocalDate.of(2010, 4, 30)).setAddress("123 Sesame St").setGender(Gender.female)));
        family = familyRepository.save(new Family().setFather(father).setMother(mother).setChildren(children));
    }
    
    /**
     * Ensure that a single person can be retrieved
     * 
     * @throws Exception 
     */
    @Test
    public void readPeople() throws Exception {
        mockMvc.perform(get("/people"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.people[0].firstName", is("Al")))
                .andExpect(jsonPath("$._embedded.people[0].lastName", is("Bundy")))
                .andExpect(jsonPath("$._embedded.people[0].birthDate", is("1960-01-02")))
                .andExpect(jsonPath("$._embedded.people[0].address", is("123 Sesame St")))
                .andExpect(jsonPath("$._embedded.people[0].gender", is("male")))
                .andExpect(jsonPath("$._embedded.people[1].firstName", is("Peg")))
                .andExpect(jsonPath("$._embedded.people[1].lastName", is("Bundy")))
                .andExpect(jsonPath("$._embedded.people[1].birthDate", is("1981-02-28")))
                .andExpect(jsonPath("$._embedded.people[1].address", is("123 Sesame St")))
                .andExpect(jsonPath("$._embedded.people[1].gender", is("female")));
    }

    /**
     * Test deleting a person that is a father from a family.  Should be successful and leave family with a null father.
     * 
     * @throws Exception
     */
    @Test
    public void deleteFather() throws Exception {
        // Need to delete family first to avoid referential constraint
        mockMvc.perform(delete("/family/" + family.getId())).andExpect(status().isNoContent());
        mockMvc.perform(delete("/people/" + father.getId())).andExpect(status().isNoContent());
        Family updatedFamily = familyRepository.findOne(family.getId());
        assertNull(updatedFamily);
    }
    
    /**
     * Convert object to JSON string
     * 
     * @param o
     *            Object to convert
     * @return JSON
     * @throws IOException
     */
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
