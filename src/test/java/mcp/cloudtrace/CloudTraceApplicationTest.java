package mcp.cloudtrace;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloudTraceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CloudTraceApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository deviceRepository;

    @Test
    public void testShouldFindAllDevices() throws Exception {
        Mockito.when(deviceRepository.findAll())
                .thenReturn(
                        Arrays.asList(
                                new Client(1L, "IOS"),
                                new Client(2L, "Android"),
                                new Client(3L, "WEB")

                        )
                );

        mockMvc.perform(get("/clients")
                .header("client-id", "Android"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("@.[0].id").isNotEmpty());
    }

}