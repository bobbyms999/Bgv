package com.accredilink.bgv.component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LobHelper {

	private final DataSource ds;

	public LobHelper(@Autowired DataSource ds) {
		this.ds = ds;
	}

	public Blob createBlob(byte[] content) {
		try (Connection conn = ds.getConnection()) {
			Blob b = conn.createBlob();
			try (OutputStream os = b.setBinaryStream(1); InputStream is = new ByteArrayInputStream(content)) {
				byte[] buffer = new byte[500000];
				int len;
				while ((len = is.read(buffer)) > 0) {
					os.write(buffer, 0, len);
				}
				return b;
			}
		} catch (Exception e) {
		}
		return null;
	}

}
