#!/bin/bash

cd ../frontend
yarn build # npm run build でも良い

cd ../backend
mvn clean package