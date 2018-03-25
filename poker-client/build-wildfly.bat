@cd web-app

set PUBLIC_URL=/wildfly
del build-wildfly /Q
npm run-script build && ren build build-wildfly
