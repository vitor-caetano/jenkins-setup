#!groovy

import hudson.*
import hudson.model.*
import hudson.security.*
import jenkins.*
import jenkins.model.*
import java.util.*
import com.michelin.cio.hudson.plugins.rolestrategy.*
import java.lang.reflect.*

def env = System.getenv()

// Roles
def globalRoleRead = "read"
def globalRoleAdmin = "admin"
def ldapGroupNameAdmin = env['LDAP_GROUP_NAME_ADMIN'] ?: "administrators"

def jenkinsInstance = Jenkins.getInstance()
def currentAuthenticationStrategy = Hudson.instance.getAuthorizationStrategy()

Thread.start {
   // Set new authentication strategy
   RoleBasedAuthorizationStrategy roleBasedAuthenticationStrategy = new RoleBasedAuthorizationStrategy()
   jenkinsInstance.setAuthorizationStrategy(roleBasedAuthenticationStrategy)

   Constructor[] constrs = Role.class.getConstructors();
   for (Constructor<?> c : constrs) {
       c.setAccessible(true);
   }

   // Make the method assignRole accessible
   Method assignRoleMethod = RoleBasedAuthorizationStrategy.class.getDeclaredMethod("assignRole", String.class, Role.class, String.class);
   assignRoleMethod.setAccessible(true);

   // Create admin set of permissions
   Set<Permission> adminPermissions = new HashSet<Permission>();
   adminPermissions.add(Permission.fromId("hudson.model.Hudson.Administer"));
   adminPermissions.add(Permission.fromId("hudson.model.Hudson.Read"));

   // Create the admin Role
   Role adminRole = new Role(globalRoleAdmin, adminPermissions);
   roleBasedAuthenticationStrategy.addRole(RoleBasedAuthorizationStrategy.GLOBAL, adminRole);

   // Assign the role
   roleBasedAuthenticationStrategy.assignRole(RoleBasedAuthorizationStrategy.GLOBAL, adminRole, ldapGroupNameAdmin);

   // Read access for authenticated users
   // Create permissions
   Set<Permission> authenticatedPermissions = new HashSet<Permission>();
   authenticatedPermissions.add(Permission.fromId("hudson.model.Hudson.Read"));
   authenticatedPermissions.add(Permission.fromId("hudson.model.View.Read"));

   Role authenticatedRole = new Role(globalRoleRead, authenticatedPermissions);
   roleBasedAuthenticationStrategy.addRole(RoleBasedAuthorizationStrategy.GLOBAL, authenticatedRole);

   // Assign the role
   roleBasedAuthenticationStrategy.assignRole(RoleBasedAuthorizationStrategy.GLOBAL, authenticatedRole, 'authenticated');

   // Save the state
   jenkinsInstance.save()
}