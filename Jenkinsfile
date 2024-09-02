pipeline {
    agent any
    environment {
            PATH = "CC:/Users/e031807/maven/apache-maven-3.9.6-bin/apache-maven-3.9.6/bin${env.PATH}"
        }
    stages {
        stage('Clone') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/haarish04/TelConnect']]
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
