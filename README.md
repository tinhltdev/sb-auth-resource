# this branch is a customization for a central authorization server, rather than using Keycloak directly as one
  The flow as bellow:
  	  user login -> this repo -> call to Keycloark for auth -> if ok return to this repo + store the user's session in the redis -> return token to user
  	  
#TODO: create a register api

#Keycloak can be run in standalone mode or embedded mode 
#Keycloak Authorization
- Resources 1:n Scopes
- Policies
- Permissions is used to associate a Resource and scopes with policies
--> The file policy-enforcer.json is used to make a decision. "name" and "scope" will be used to check.
-- Using Authorization Evaluate to check: http://localhost:8180/admin/master/console/#/msb/clients/3c60434b-0681-4d34-ace8-02ce7dc0c057/authorization/evaluate
   + notice that Overall Results based on Decision strategy setting: affirmative or Unanimous
   + Many of permissions may be permitted, but if the overall result is not as you expected, please check those permissions to see whether they are associated with the correct scope

-- Thường thì ta sẽ đi từ: Resource và scope --> tạo quyền(permission), cần quyền gì để được phép access resource và scope đó  -> ai có quyền đó(policy)
-- Khi thực hiện test evaluate thì Keycloak merge giữa roles được chọn khi test, với roles thực tế nó đang được assign