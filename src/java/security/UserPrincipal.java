package security;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
REF: http://scytl.github.io/restguide/#_security_2
*/
public class UserPrincipal implements Principal {
 private String username; 

  private List<String> roles = new ArrayList<>();

  
  public UserPrincipal(String username, List<String> roles) {
    super();
    this.username = username;
    this.roles = roles;
  }

  @Override
  public String getName() { 
    return username;
  }

  public boolean isUserInRole(String role) { 
    return this.roles.contains(role);
  }
}


//public class UserPrincipal implements Principal {
//
//  private String username; 
//  private String role;
//
//  public UserPrincipal(String username, String role) {
//    super();
//    this.username = username;
//    this.role = role;
//  }
// 
//
//  @Override
//  public String getName() { 
//    return username;
//  }
//
//  public boolean isUserInRole(String role) { 
//    return this.role.equals(role);
//  }
//}