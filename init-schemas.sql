create schema if not exists keycloak;
create schema if not exists app_accounts;
create schema if not exists app_cash;
create schema if not exists app_transfer;
create schema if not exists app_notifications;

grant all on schema keycloak to myuser;
grant all on schema app_accounts to myuser;
grant all on schema app_cash to myuser;
grant all on schema app_transfer to myuser;
grant all on schema app_notifications to myuser;