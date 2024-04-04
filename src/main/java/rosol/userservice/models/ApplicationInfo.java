package rosol.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;



public class ApplicationInfo {
        @JsonIgnore
        private String instanceId;
        @JsonIgnore
        private String hostName;
        @JsonIgnore
        private String app;
        private String ipAddr;
        @JsonIgnore
        private String status;
        @JsonIgnore
        private String overriddenStatus;
        private Port port;
        @JsonIgnore
        private String securePort;

        public String getSecurePort() {
            return securePort;
        }
        @JsonIgnore
        private int countryId;
        @JsonIgnore
        private String dataCenterInfo;
        @JsonIgnore
        private String leaseInfo;
        @JsonIgnore
        private String metadata;
        @JsonIgnore
        private String homePageUrl;
        @JsonIgnore
        private String statusPageUrl;
        @JsonIgnore
        private String healthCheckUrl;
        @JsonIgnore
        private String vipAddress;
        @JsonIgnore
        private String secureVipAddress;
        @JsonIgnore
        private String isCoordinatingDiscoveryServer;
        @JsonIgnore
        private String lastUpdatedTimestamp;
        @JsonIgnore
        private String lastDirtyTimestamp;
        @JsonIgnore
        private String actionType;

        public String getIpAddr() {
            return ipAddr;
        }

        public Port getPort() {
            return port;
        }

        public String getInstanceId() {
            return instanceId;
        }

        public String getHostName() {
            return hostName;
        }

        public String getApp() {
            return app;
        }

        public String getStatus() {
            return status;
        }

        public String getOverriddenStatus() {
            return overriddenStatus;
        }

        public int getCountryId() {
            return countryId;
        }

        public String getDataCenterInfo() {
            return dataCenterInfo;
        }

        public String getLeaseInfo() {
            return leaseInfo;
        }

        public String getMetadata() {
            return metadata;
        }

        public String getHomePageUrl() {
            return homePageUrl;
        }

        public String getStatusPageUrl() {
            return statusPageUrl;
        }

        public String getHealthCheckUrl() {
            return healthCheckUrl;
        }

        public String getVipAddress() {
            return vipAddress;
        }

        public String getSecureVipAddress() {
            return secureVipAddress;
        }

        public String getIsCoordinatingDiscoveryServer() {
            return isCoordinatingDiscoveryServer;
        }

        public String getLastUpdatedTimestamp() {
            return lastUpdatedTimestamp;
        }

        public String getLastDirtyTimestamp() {
            return lastDirtyTimestamp;
        }

        public String getActionType() {
            return actionType;
        }
    }

