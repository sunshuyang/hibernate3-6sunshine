package cn.itcast.m_session_manage;

import org.hibernate.Session;

public class Utils {

	public static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

}
