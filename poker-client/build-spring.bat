@cd web-app

set PUBLIC_URL=/spring
del build-spring /Q
npm run-script build && ren build build-spring
