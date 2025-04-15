#!/bin/bash
set -x

echo "--------------  mysql drop user-----------------------"
echo 

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'acceso'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'acceso'@'%' identified by '7kMxd7iP'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'acepta'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'acepta'@'%' identified by 'Tha7Ye7I'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'actis'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'actis'@'%' identified by 'zPS2Smyt1JQ56'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON actis.* to 'actis'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'anexos'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'anexos'@'%' identified by 'Q6AWy97yoJm4o'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'bodega'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON bodega.* to 'bodega'@'%' identified by 'YNsw45bs'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'boletas'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON bolegas.* to 'boletas'@'%' identified by 'iPf1Op1m'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'boletas'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'buzon'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'buzon'@'%' identified by 'gj5Jh7Cq'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'contable'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'contable'@'%' identified by 'H78Rteij_'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'diplade'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'diplade'@'%' identified by 'Pe7zaegi'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'divac'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'divac'@'%' identified by 'zmc5VH9p_'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'divac_sga'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'divac_sga'@'%' identified by 'zmc5VH9p_'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'divac_user'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'divac_user'@'%' identified by 'ohp6Aath_'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2022.* to 'dosporciento'@'%' identified by '2fK8ZFEcjb2Ur'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2021.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2020.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2019.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2018.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2017.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2016.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2015.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2014.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2013.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON dosporciento2012.* to 'dosporciento'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'dte'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'dte'@'%' identified by 'jws96tGX'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'dte_documentos'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'dte_documentos'@'%' identified by 'uH5mei5u'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'dte_onlyoffice'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'dte_onlyoffice'@'%' identified by 'biej5Pa5'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'espacios'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON espacios_publicos.* to 'espacios'@'%' identified by 'nx2rw1YJ'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

#mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER fic_r'@'%'"
#mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON espacios_publicos.* to 'espacios'@'%' identified by 'nx2rw1YJ'"
#mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'firmas'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON firmas.* to 'firmas'@'%' identified by 'oo5ieCul'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'form29'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON form29.* to 'form29'@'%' identified by 'uoN0aidu'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'jdiplade'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'jdiplade'@'%' identified by 'uwohZ8nu0OolaN'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'ofp2'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'ofp2'@'%' identified by 'uP1dr5Dy'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'ofpi2'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'ofpi2'@'%' identified by 'c46wFSva'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'pagos'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'pagos'@'%' identified by 'yalN10Hi'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'postulacion'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'postulacion'@'%' identified by 'oaMm82eF_'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'preinversion'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'preinversion'@'%' identified by '6mbve1L9zEnn2m'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

#mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'replica'@'%'"
#mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'resoluciones'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON documentos.* to 'resoluciones'@'%' identified by 'bX10Yapz'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'revision'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'revision'@'%' identified by '6mbve1L9zEnn2m'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'sagir'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'sagir'@'%' identified by 'ooth3ieY'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'sagir_clasifi'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'sagir_clasifi'@'%' identified by 'yh44bJPc'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'sagir_honorarios'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'sagir_honorarios'@'%' identified by '4dnsdUC9'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'sagir_pro'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sagir.* to 'sagir_pro'@'%' identified by 'ahy0ohD6'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'sga'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'sga'@'%' identified by '_XGbjve22'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'sgd'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'sgd'@'%' identified by 'mOgiUeKQ9zja335'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'sgd_editor'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'sgd_editor'@'%' identified by 'mOgiUeKQ9zja335'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'sistemas'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON sga.* to 'sistemas'@'%' identified by 'rei9Aa7i'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP USER 'telefonia'@'%'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "GRANT ALL PRIVILEGES ON telefonia.* to 'telefonia'@'%' identified by 'Yu2Eibij'"
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "FLUSH PRIVILEGES"


echo "--------------  mysql dump -------------------------"
echo 
mysqldump -u system -h $SERVER_MYSQL_IP --port=$SERVER_MYSQL_PORT -p$SERVER_MYSQL_PASSWORD sagir > /tmp/sagir.sql


echo "--------------  mysql drop -------------------------"
echo 
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "DROP DATABASE sagir"


echo "--------------  mysql create -----------------------"
echo 
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "CREATE DATABASE sagir"


echo "--------------  mysql restore ----------------------"
echo 
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD sagir < /tmp/sagir.sql 
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD -e "TRUNCATE TABLE sagir.logs"

echo "--------------  mysql update passwod ---------------"
echo 
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD --port=$HOST_MYSQL_PORT -e "UPDATE sagir.users SET password='\$2a\$10\$Xij9NzDxBdWtqo//nKM8VeTsIVwO1YepLT1ySIqSJ3q1FmaJ2QAwi' "
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD --port=$HOST_MYSQL_PORT -e "UPDATE sagir.post_account SET password='\$2a\$10\$Xij9NzDxBdWtqo//nKM8VeTsIVwO1YepLT1ySIqSJ3q1FmaJ2QAwi' "
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD --port=$HOST_MYSQL_PORT -e "UPDATE sagir.post_user SET password='\$2a\$10\$Xij9NzDxBdWtqo//nKM8VeTsIVwO1YepLT1ySIqSJ3q1FmaJ2QAwi' "

mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD --port=$HOST_MYSQL_PORT -e "UPDATE sagir.post_antecedente SET email='hsalinas@gobiernosantiago.cl' "
mysql -u system -h $HOST_MYSQL_IP --port=$HOST_MYSQL_PORT -p$HOST_MYSQL_PASSWORD --port=$HOST_MYSQL_PORT -e "UPDATE sagir.post_task SET enabled=FALSE"
