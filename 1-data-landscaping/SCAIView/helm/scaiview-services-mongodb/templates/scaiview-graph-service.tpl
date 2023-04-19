apiVersion: monitoring.coreos.com/v1
kind: ServiceMonitor
metadata:
  name: {{ .Release.Name }}-graph-service-monitor
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: monitoring
    deployment-id:  {{ .Release.Name }}
    namespace: {{ .Release.Namespace }}
spec:
  jobLabel: {{ .Release.Name }}-graph-service-monitor-metrics
  selector:
    matchLabels:
      product: scaiview
      component: graph-service
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
  name: {{ .Release.Name }}-graph-service-service
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: graph-service
    deployment-id:  {{ .Release.Name }}
spec:
  ports:
    - port: 8092
      name: status
  selector:
    product: scaiview
    component: graph-service
    deployment-id:  {{ .Release.Name }}
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Release.Name }}-graph-service-deployment
  labels:
{{- include "scaiview-services.labels" . | nindent 4 }}
    product: scaiview
    component: graph-service
    deployment-id:  {{ .Release.Name }}
spec:
  selector:
    matchLabels:
      product: scaiview
      component: graph-service
      deployment-id:  {{ .Release.Name }}
  replicas: 1
  template:
    metadata:
      labels:
        product: scaiview
        component: graph-service
        deployment-id:  {{ .Release.Name }}
    spec:
      containers:
      - name: container
        image: docker.arty.scai.fraunhofer.de/scaiview-graphdata-service:{{ .Values.scaiview.api.version }}
        imagePullPolicy: Always
        ports:
        - containerPort: 8092
        - containerPort: 5005 # debug port
        env:
        - name: MANAGEMENT_SERVER_PORT
          value: "8092"
          # ARTEMIS
{{ toYaml .Values.scaiview.infra.broker | indent 8}}
{{ toYaml .Values.scaiview.infra.orient | indent 8}}
        - name: JAVA_TOOL_OPTIONS # enable debugging
          value: "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
        readinessProbe:
            httpGet:
                path: /actuator/health
                port: 8092
            initialDelaySeconds: 15
            periodSeconds: 30
        livenessProbe:
            httpGet:
                path: /actuator/health
                port: 8092
            initialDelaySeconds: 60
            periodSeconds: 30
