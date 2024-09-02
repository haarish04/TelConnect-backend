pipeline {
    agent any
    environment {
    stages {
        stage('Clone') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: '
https://github.com/haarish04/TelConnect']]
                ])
            }
        }
        stage("Maven") {
            steps {
                bat '''
                mvn install
                '''
            }
        }

    }
}