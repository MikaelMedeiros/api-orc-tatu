# api-orc-tatu

Para funcionar localmente com o springsecurity desabilitado e o h2 em memória 
adicione o seguinte comando nos argumentos da VM:

```
-Dspring.profiles.active=local
```
Para funcionar com spring security e preciso adicionar suas credenciais do google, nas variaveis de ambiente:

```
CLIENT_SECRET
CLIENT_ID
```
Lembrando que sua configuração do google claudio deve ficar de acordo com a imagem abaixo.  
![img.png](img.png)