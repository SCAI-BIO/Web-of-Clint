apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: {{ .Release.Name }}-api-service-ingress
  annotations:
    nginx.ingress.kubernetes.io/proxy-connect-timeout: "300s"
    nginx.ingress.kubernetes.io/proxy-send-timeout: "300s"
    nginx.ingress.kubernetes.io/proxy-read-timeout: "300s"
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: api-service
    deployment-id:  {{ .Release.Name }}
spec:
  rules:
  - host: {{ .Values.scaiview.api.domain }}
    http:
       paths:
       - path: /
         pathType: Prefix
         backend:
           service:
             name: {{ .Release.Name }}-api-service-service
             port:
               number: 8081
---
apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  name: {{ .Release.Name }}-api-service-monitor
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: monitoring
    deployment-id:  {{ .Release.Name }}
    namespace: {{ .Release.Namespace }}
spec:
  jobLabel: {{ .Release.Name }}-api-service-monitor-metrics
  selector:
    matchLabels:
      product: scaiview
      component: api-service
      deployment-id:  {{ .Release.Name }}
  namespaceSelector:
    matchNames:
      - {{ .Release.Namespace }}
  endpoints:
    - targetPort: 8092
      path: /actuator/prometheus
---
apiVersion: v1
kind: Service
metadata:
  name: {{ .Release.Name }}-api-service-service
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: api-service
    deployment-id:  {{ .Release.Name }}
spec:
  ports:
    - port: 8081
      name: web
    - port: 8092
      name: status
  selector:
    product: scaiview
    component: api-service
    deployment-id:  {{ .Release.Name }}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Release.Name }}-api-service-deployment
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: api-service
    deployment-id:  {{ .Release.Name }}
spec:
  selector:
    matchLabels:
      product: scaiview
      component: api-service
      deployment-id:  {{ .Release.Name }}
  replicas: 1
  template:
    metadata:
      labels:
        product: scaiview
        component: api-service
        deployment-id:  {{ .Release.Name }}
    spec:
      containers:
      - name: container
        image: docker.arty.scai.fraunhofer.de/scaiview-api-service:{{ .Values.scaiview.api.version }}
        imagePullPolicy: Always
        ports:
          - containerPort: 8081
          - containerPort: 8092
          - containerPort: 5005 # debug port
        env:
        - name: SPRING_MAIN_ALLOW-BEAN-DEFINITION-OVERRIDING
          value: "true"
        - name: MANAGEMENT_SERVER_PORT 
          value: "8092"
        - name: SERVER_PORT 
          value: "8081"
        # bikmi
{{ toYaml .Values.scaiview.infra.bikmi | indent 8}}
        # ARTEMIS
{{ toYaml .Values.scaiview.infra.broker | indent 8}}
        # OIDC
{{ toYaml .Values.scaiview.infra.oidc | indent 8}}
        - name: JAVA_TOOL_OPTIONS # enable debugging
          value: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
        readinessProbe:
            httpGet:
                path: /actuator/health
                port: 8092
            initialDelaySeconds: 30
            periodSeconds: 60
            failureThreshold: 5
        livenessProbe:
            httpGet:
                path: /actuator/health
                port: 8092
            initialDelaySeconds: 300
            periodSeconds: 30

