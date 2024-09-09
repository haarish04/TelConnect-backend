pipeline {
    agent any

    environment {
        // Update the Maven path accordingly
        PATH = "C:/Users/e031975/Downloads/apache-maven-3.9.8-bin/apache-maven-3.9.8/bin;${env.PATH}"
        NODE_VERSION = '22.x'  // Update Node.js version to 22.x
    }

    stages {
        stage('Clone Repositories') {
            parallel {
                stage('Clone Backend') {
                    steps {
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: '*/main']],
                            userRemoteConfigs: [[url: 'https://github.com/haarish04/TelConnect']]
                        ])
                    }
                }
//                 stage('Clone Frontend') {
//                     steps {
//                         dir('./frontend') {
//                             checkout([
//                                 $class: 'GitSCM',
//                                 branches: [[name: '*/main']],
//                                 userRemoteConfigs: [[url: 'https://github.com/haarish04/telConnect-app.git']]
//                             ])
//                         }
//                     }
//                 }
            }
        }

        stage('Build Backend with Maven') {
            steps {
                dir('') {
                    // Maven build for the backend
                    bat 'mvn clean install'
                }
            }
        }

//         stage('Run Backend Tests') {
//             steps {
//                 dir('') {
//                     // Run backend test cases
//                     bat 'mvn test'
//                 }
//             }
//         }

//         stage('Install Frontend Dependencies') {
//             steps {
//                 // Use Node.js v22.x environment for frontend
//                 bat '''
//                 cd frontend
//                 dir
//                 dir
//                 npm install
//                 '''
//             }
//         }
//
//         stage('Build Frontend') {
//             steps {
//                 // Build the React-Vite frontend application using Node.js v22.x
//                 bat '''
//                 cd frontend
//                 dir
//                 dir
//                 npm install
//                 '''
//             }
//         }


    }

    post {
        always {
            cleanWs() // Clean workspace after execution
        }
        success {
            echo 'Build and run succeeded!'
        }
        failure {
            echo 'Build failed.'
        }
    }
}
