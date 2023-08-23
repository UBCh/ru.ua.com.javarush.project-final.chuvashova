package com.javarush.jira.datasource;

import com.javarush.jira.bugtracking.UserBelong;
import com.javarush.jira.bugtracking.attachment.Attachment;
import com.javarush.jira.bugtracking.project.Project;
import com.javarush.jira.bugtracking.sprint.Sprint;
import com.javarush.jira.bugtracking.task.Activity;
import com.javarush.jira.bugtracking.task.Task;
import com.javarush.jira.login.User;
import com.javarush.jira.mail.internal.MailCase;
import com.javarush.jira.profile.internal.model.Contact;
import com.javarush.jira.profile.internal.model.Profile;
import com.javarush.jira.ref.internal.Reference;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

@Component
@Data
@AllArgsConstructor
public class H2DBconnect {


    public SessionFactory sessionFactory;


    @Value("${spring.path}")
    private String path;


    @Value("${dialect}")
    private String dialect;


    @Value("${driver}")
    private String driver;


    @Value("${url}")
    private String url;


    @Value("${username}")
    private String username;


    @Value("${password}")
    private String password;


    public H2DBconnect() throws IOException {
	this.sessionFactory = getNewSessionFactory(propertiesSessionProviderH2DB());
	runSqlScriptFile(path);

    }


    public Properties propertiesSessionProviderH2DB() {
	Properties properties = new Properties();
	properties.put(Environment.DIALECT, dialect);
	properties.put(Environment.DRIVER, driver);
	properties.put(Environment.URL, url);
	properties.put(Environment.USER, username);
	properties.put(Environment.PASS, password);
	properties.put(Environment.HBM2DDL_AUTO, "update");
	properties.put(Environment.AUTOCOMMIT, false);
	properties.put(Environment.SHOW_SQL, true);
	properties.put(Environment.FORMAT_SQL, true);
	properties.put(Environment.HIGHLIGHT_SQL, true);
	return properties;
    }


    public SessionFactory getNewSessionFactory(Properties properties) {
	return new Configuration()
		.addProperties(properties)
		.addAnnotatedClass(Attachment.class)
		.addAnnotatedClass(Project.class)
		.addAnnotatedClass(Sprint.class)
		.addAnnotatedClass(Activity.class)
		.addAnnotatedClass(Task.class)
		.addAnnotatedClass(UserBelong.class)
		.addAnnotatedClass(User.class)
		.addAnnotatedClass(MailCase.class)
		.addAnnotatedClass(Contact.class)
		.addAnnotatedClass(Profile.class)
		.addAnnotatedClass(Reference.class)
		.buildSessionFactory();

    }


    private void runSqlScriptFile(String p) throws IOException {
	String sqlScript = new String(Files.readAllBytes(Paths.get(p)));
	SessionFactory scriptSessionFactory = sessionFactory;
	Session scriptSession = scriptSessionFactory.openSession();
	scriptSession.beginTransaction();
	NativeQuery nativeQuery = scriptSession.createNativeQuery(sqlScript);
	nativeQuery.executeUpdate();

	scriptSession.getTransaction().commit();
    }

}
