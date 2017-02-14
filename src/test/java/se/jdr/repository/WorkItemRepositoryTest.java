package se.jdr.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import se.jdr.config.TestConfig;
import se.jdr.model.WorkItem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public final class WorkItemRepositoryTest {

    @MockBean
    WorkItemRepository workItemRepository;

    private String title;
    private String description;
    WorkItem workItem = new WorkItem(title, description);

    @Test
    public void canFindWorkItemByStatus() {
        WorkItem.Status status = workItem.getStatus();
        when(workItemRepository.findByStatus(status)).thenReturn(Stream.of(workItem).collect(Collectors.toList()));
        List<WorkItem> workItems = workItemRepository.findByStatus(status).stream().collect(Collectors.toList());
        WorkItem workItemfromDb = workItems.get(0);
        assertThat(workItemfromDb, is(workItemfromDb));
    }

    @Test
    public void canFindWorkitemByTeamId() {

        when(workItemRepository.findByDescription(description)).thenReturn(Stream.of(workItem).collect(Collectors.toList()));
        List<WorkItem> workItems = workItemRepository.findByDescription(description).stream().collect(Collectors.toList());
        WorkItem workItemfromDb = workItems.get(0);
        assertThat(workItemfromDb, is(workItem));

    }
}
