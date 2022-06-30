Paramétrage et préparation de l'environnement keyloak

1- Installation keyloak version 15.0.2
-----------------------------------
      KEYCLOAK_DIR = /{repertoire-installation-keycloak}/

     - vous pouvez charger le paramétrage (Création du realm, client, des roles et utilisateurs) via l'importation du fichier  realm-export.json qui se trouve dans le dossier fixtures.

	 - vous devez copier le dossier theme (voir le dossier fixtures) dans /KEYCLOAK_DIR/themes/  pour la personnalisation de la page login avec le logo de du RAM.

     -Démarrage de keyloak  
      La commande suivante permet le lancement de keycloak sur le port 8086 via la ligne de commande depuis le répertoire racine qui contient le dossier d'installation de keycloak : cd KEYCLOAK_DIR 

      ./bin/standalone.sh  -Djboss.socket.binding.port-offset=6


 -  vous devez cloner le projet config-server dans un repertoire dans le serveur et changer le chemain dans application.yml (ram-kpi-gateway-ms)




