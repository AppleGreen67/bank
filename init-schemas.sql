create schema if not exists keycloak;
create schema if not exists app_accounts;

grant all on schema keycloak to myuser;
grant all on schema app_accounts to myuser;