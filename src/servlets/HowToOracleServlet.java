/* Basic Servlet/Oracle connectivity Test */
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class HowToOracleServlet extends HttpServlet

{
public void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException

{

response.setContentType("Text/html");
PrintWriter out = response.getWriter();
out.println("<HTML>");
out.println("<HEAD><TITLE>Basic Servlet/Oracle Query Example </TITLE></HEAD>");
out.println("<BODY BGCOLOR=\"#ffffcc\">");
out.println("<CENTER>");
out.println("<B>Employees</B>");
out.println("<BR><BR>");
Connection conn = null;

try

{

//Class.forName("oracle.jdbc.driver.OracleDriver"); 
//conn = DriverManager.getConnection("jdbc:oracle:thin:@oracle.localhost.com:1521:orcl","scott","tiger"); 

Context initContext = new InitialContext();
Context envContext  = (Context)initContext.lookup("java:/comp/env");
DataSource ds = (DataSource)envContext.lookup("jdbc/myoracle");
conn = ds.getConnection();


Statement stmt = conn.createStatement(); 
ResultSet rs = stmt.executeQuery("SELECT * FROM scott.Emp"); 

//Print start of table and column headers 
out.println("<TABLE CELLSPACING=\"0\" CELLPADDING=\"3\" BORDER=\"1\">"); 
out.println("<TR><TH>ID</TH><TH>NAME</TH><TH>SURNAME</TH>"); 
out.println(" <TH>SALARY</TH><TH>STARTDATE</TH></TR>"); 


//Loop through results of query. 

while(rs.next()) 
{ 
out.println("<TR>");
out.println(" <TD>" + rs.getString("EMPNO") + "</TD>");
out.println(" <TD>" + rs.getString("ENAME") + "</TD>");
out.println(" <TD>" + rs.getString("JOB") + "</TD>");
out.println(" <TD>" + rs.getInt("SAL") + "</TD>");
out.println(" <TD>" + rs.getString("HIREDATE") + "</TD>");
out.println("</TR>"); 
} 

out.println("</TABLE>");
} 
catch(NamingException ne){
ne.printStackTrace();
}

catch(SQLException e) 

{ 
out.println("SQLException: " + e.getMessage() + "<BR>"); 
while((e = e.getNextException()) != null) 
out.println(e.getMessage() + "<BR>"); 
} 

catch(Exception e){
 e.printStackTrace();
}

finally 
{ 
//Clean up resources, close the connection. 
if(conn != null) 
{ 
try 
{ 
conn.close(); 
} 
catch (Exception ignored) {} 
} 
} 

out.println("</CENTER>"); 
out.println("</BODY>"); 
out.println("</HTML>"); 

} 
} 
