pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build and Unit Test') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Deployment') {
            stage('Production') {
                      when {
                        branch 'main'
                      }
                      steps {
                        withAWS(region:'<your-bucket-region>',credentials:'<AWS-Staging-Jenkins-Credential-ID>') {
                          s3Delete(bucket: '<bucket-name>', path:'**/*')
                          s3Upload(bucket: '<bucket-name>', workingDir:'build', includePathPattern:'**/*');
                        }
                  }
            }
        }
    }
}

