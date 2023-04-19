apiVersion: monitoring.coreos.com/v1
kind: Prometheus
metadata:
  name: {{ .Release.Name }}-prometheus
  labels:
    product: scaiview
    component: monitoring
    deployment-id: {{ .Release.Name }}
spec:
  replicas: 2
  serviceAccountName: bioservices-sa-user
  serviceMonitorSelector:
    matchLabels:
      product: scaiview
      component: monitoring
      deployment-id: {{ .Release.Name }}