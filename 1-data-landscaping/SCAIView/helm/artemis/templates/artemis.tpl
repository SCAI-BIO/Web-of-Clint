##
# The public helm chart is unavailable and the installation was buggy, hence the self baked version.
apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  name: {{ .Release.Name }}-service-monitor
  labels:
      product: scaiview
      component: monitoring
      deployment-id: {{ .Values.scaiview.deploymentid }}
spec:
  jobLabel: {{ .Release.Name }}-service-monitor-metrics
  selector:
    matchLabels:
        component: broker-service
        version: {{ .Chart.AppVersion }}
        deployment-id:  {{ .Release.Name }}
  namespaceSelector:
    matchNames:
      - bio
  endpoints:
    - targetPort: 8092
      path: /
---
apiVersion: v1
kind: Service
metadata:
  name: {{ .Release.Name }}-service
  labels:
    component: broker-service
    version: {{ .Chart.AppVersion }}
    deployment-id:  {{ .Release.Name }}
spec:
  ports:
    - port: 8161
      name: web
    - port: 61616
      name: proto
    - port: 9404
      name: monitoring
  selector:
    component: broker-service
    version: {{ .Chart.AppVersion }}
    deployment-id:  {{ .Release.Name }}
---
apiVersion: v1
kind: Secret
metadata:
  name: {{ .Release.Name }}-service-secret
type: Opaque
data:
  artemis-password: # echo -n 'your_password' | base64
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Release.Name }}-service-deployment
  labels:
    component: broker-service
    version: {{ .Chart.AppVersion }}
    deployment-id:  {{ .Release.Name }}
spec:
  selector:
    matchLabels:
        component: broker-service
        version: {{ .Chart.AppVersion }}
        deployment-id:  {{ .Release.Name }}
  replicas: 1 # tells deployment to run 2 pods matching the template
  template:
    metadata:
      labels:
        component: broker-service
        version: {{ .Chart.AppVersion }}
        deployment-id:  {{ .Release.Name }}
    spec:
      containers:
        - name: artemis
          image:  vromero/activemq-artemis:{{ .Chart.AppVersion }}
          imagePullPolicy: Always
          ports:
            - containerPort: 8161 # web ui
            - containerPort: 9404 # prometheus
            - containerPort: 61616 # queue
          #   - containerPort: 5005 # debug port
          env:
            - name: ENABLE_JMX_EXPORTER
              value: "true"
            - name: ARTEMIS_MIN_MEMORY
              value: "1512M"
            - name: ARTEMIS_MAX_MEMORY
              value: "3048M"
            - name: ARTEMIS_USERNAME
              value: "artemis"
            - name: ARTEMIS_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: winning-liger-activemq-artemis
                  key:  artemis-password

