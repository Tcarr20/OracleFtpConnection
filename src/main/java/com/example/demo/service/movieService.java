package com.example.demo.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class movieService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private static final SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");

	@Value("${sftp.host}")
	private String sftpHost;

	@Value("${sftp.port:22}")
	private int sftpPort;

	@Value("${sftp.user}")
	private String sftpUser;

	@Value("${sftp.privateKey:#{null}}")
	private Resource sftpPrivateKey;

	@Value("${sftp.privateKeyPassphrase:}")
	private String sftpPrivateKeyPassphrase;

	@Value("${sftp.password:#{null}}")
	private String sftpPasword;

	@Value("${sftp.remote.directory:/}")
	private String sftpRemoteDirectory;

	//@Scheduled(cron = "0 15 0 * * ?")
	@Scheduled(cron = "*/15 * * * * ?")
	public void scheduledTask()  {
		String fileName = "MoviesFile" + df.format(new Date()) + ".txt";
		
		FileWriter moviesFile;
		try {
			File file = new File(fileName);
			moviesFile = new FileWriter(file);
			PrintWriter outFile = new PrintWriter(moviesFile,false);
			jdbcTemplate.query("SELECT * FROM movies", (rs)-> {
				writeToFile(outFile, rs.getString("title"),  rs.getString("description"));
				});
			outFile.close();
			System.out.println("Hello world" + df.format(new Date()));

			JSch jSch = new JSch();
			Session session = jSch.getSession(sftpUser,sftpHost);
			session.setPassword(sftpPasword);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking","no");
			session.setConfig(config);
			session.connect();
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.put(String.valueOf(file),String.valueOf(file));
			channelSftp.disconnect();
			session.disconnect();

		} catch (IOException | JSchException | SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeToFile(PrintWriter outFile, String title, String description ) {
			outFile.printf("%-30.30s  %-30.30s%n", title ,description);
	}

}
