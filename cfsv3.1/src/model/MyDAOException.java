//Hua-Ming Lee
//huamingl
//08-600
//hw9
//2014/12/1

package model;

public class MyDAOException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public MyDAOException(Exception e) { super(e); }
	public MyDAOException(String s)    { super(s); }
}
