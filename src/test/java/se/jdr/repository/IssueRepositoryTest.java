package se.jdr.repository;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import se.jdr.config.TestConfig;
import se.jdr.model.Issue;
import se.jdr.model.WorkItem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class IssueRepositoryTest {

	@MockBean
	IssueRepository issueRepository;

	@Test
	public void canFindByWorkitemId() {
		WorkItem workItem = new WorkItem("Title", "descrption");
		Issue issue = new Issue(workItem, "title1");
		Issue issue1 = new Issue(workItem, "title2");

		when(issueRepository.findByWorkItemId(1L)).thenReturn(Stream.of(issue,issue1).collect(Collectors.toList()));
		List<Issue> issues = issueRepository.findByWorkItemId(1L).stream().collect(Collectors.toList());
		assertThat(issues, hasItems(issue, issue1));
	}

}
