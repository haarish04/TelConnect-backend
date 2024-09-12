pipeline {
    agent any
//     environment {
//         PATH = "C:/Users/e039325/Downloads/apache-maven-3.9.8-bin/apache-maven-3.9.8/bin;${env.PATH}"
//     }
    stages {
        stage('Clone') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[url: 'https://github.com/haarish04/TelConnect.git']]
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
        stage("Pull an image to check if docker exists"){
            steps{
                bat "docker pull alpine"
            }
        }
        stage("Build Docker image") {
            steps {
                script {
                    try{
                        bat "docker rmi -f TelConnect"
                        echo "REMOVED existing docker image and building a new one"
                    }
                    catch(Exception e){
                        echo "Exception occurred "+e.toString()
                    }
                    bat "docker build -t TelConnect ."
                }
            }
        }
    }
}