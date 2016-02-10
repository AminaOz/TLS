keytool -genkeypair -alias iron -keyalg RSA -validity 7 -keystore ic-keystore

keytool -list -v -keystore ic-keystore

keystore -exportcert -alias iron -keystore ic-keystore -rfc -file iron.cer
cat iron.cer

keytool -import -alias ironcert -file iron.cer -keystore ic-truststore