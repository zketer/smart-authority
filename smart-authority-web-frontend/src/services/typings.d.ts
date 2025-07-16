declare namespace API {
  interface StatsResp {
    total: number;
    todayNew: number;
    weekNew: number;
    monthNew: number;
  }

  interface UserGrowthResp {
    dates: string[];
    newUsers: number[];
    totalUsers: number[];
  }

  interface UserActivityResp {
    dates: string[];
    activeUsers: number[];
    avgOnlineTime: number[];
  }

  interface UserBehaviorResp {
    dates: string[];
    featureUsage: Record<string, number[]>;
    pageViews: Record<string, number[]>;
    avgStayTime: Record<string, number[]>;
  }
} 